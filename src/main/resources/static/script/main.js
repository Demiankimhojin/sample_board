$(document).ready(function () {
    fnInitHeader();
    fnGetBoardList();

    $('#ddlbPageSize').on('change', function () {
       $('#hdPageSize').val($('#ddlbPageSize').val());
       $('#hdPageNo').val('1');
        fnGetBoardList();
    });
});

function fnCloseAllModal() {
    $('div.modal').modal('hide');
}

function fnRegisterPopUp() {
    $('#txt_register_id').val('');
    $('#txt_register_pwd').val('');
    fnCloseAllModal();
    $('#divRegisterAccountModal').modal();
}

function fnLoginPopUp() {
    $('#txt_login_id').val('');
    $('#txt_login_pwd').val('');
    fnCloseAllModal();
    $('#divLoginModal').modal();
}

function fnSetLogin(authToken) {
    alert('로그인 성공하였습니다.');
    $.cookie("auth_token", authToken, { path: '/' });
    fnInitHeader();
}

function fnSetLogout() {
    $.removeCookie('auth_token', { path: '/' });
    fnInitHeader();
}

function fnLoginAuth() {
    if ($.trim($('#txt_login_id').val()) == '') {
        alert('로그인 아이디는 필수값 입니다.');
        return false;
    }

    if ($.trim($('#txt_login_pwd').val()) == '') {
        alert('로그인 패스워드는 필수값 입니다.');
        return false;
    }

    $.ajax({
        url: "/auth/login",
        contentType: 'application/json',
        data: '{"id": "' + $('#txt_login_id').val() + '", "password": "' + $('#txt_login_pwd').val() + '"}',
        dataType: 'json',
        type: "post",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $('#txt_login_id').val('');
            $('#txt_login_pwd').val('');
            fnSetLogin(result.auth_token);
            fnCloseAllModal();
        } else {
            alert(result.resultMsg);
        }
    });
}

function fnRegisterAccount() {
    if ($.trim($('#txt_register_id').val()) == '') {
        alert('아이디는 필수값 입니다.');
        return false;
    }

    if ($.trim($('#txt_register_pwd').val()) == '') {
        alert('패스워드는 필수값 입니다.');
        return false;
    }

    $.ajax({
        url: "/auth/register",
        contentType: 'application/json',
        data: '{"id": "' + $('#txt_register_id').val() + '", "password": "' + $('#txt_register_pwd').val() + '"}',
        dataType: 'json',
        type: "post",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $('#txt_register_id').val('');
            $('#txt_register_pwd').val('');
            alert('회원가입에 성공하였습니다.');
            fnCloseAllModal();
            fnLoginPopUp();
        } else {
            alert(result.resultMsg);
        }
    });
}

function fnAuthCheck() {
    var authToken = $.cookie("auth_token");

    if (authToken == null || authToken == "") {
        return false;
    } else {
        return true;
    }
}

function fnGetAuthToken() {
    var authToken = $.cookie("auth_token");

    if (authToken == null || authToken == "") {
        return '';
    } else {
        return authToken;
    }
}

function fnInitHeader() {
    if (fnAuthCheck()) {
        $('#divLogOutHeader').show();
        $('#divLogInHeader').hide();
    } else {
        $('#divLogInHeader').show();
        $('#divLogOutHeader').hide();
    }
}

function fnGetBoardList() {
    var content = '';

    content = '<table class="table-list">';
    content += '<thead>';
    content += '<tr class="table-thead">';
    content += '<th style="width:10%;">게시물 번호</th>';
    content += '<th style="width:65%;">게시글 제목</th>';
    content += '<th style="width:10%;">작성자</th>';
    content += '<th style="width:15%;">작성 일시</th>';
    content += '</tr>'
    content += '</thead>';
    content += '<tbody class="table-tbody">';

    $.ajax({
        url: "/board/list?pageSize=" + $('#hdPageSize').val() + "&pageNo=" + $('#hdPageNo').val(),
        type: "get",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
            content += '<tr>';
            content += '<td colspan="4" style="text-align:center;">No Data</td>';
            content += '</tr>';
            content += '</tbody>';

            $('#divBoardListArea').html(content);
            $('#divBoardPageNavy').html('');
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $.each(result.data, function (index) {
                content += '<tr onclick="fnBoardDetail(' + result.data[index].id + ')">';
                content += '<td style="text-align:center;">' + result.data[index].id + '</td>';
                if (result.data[index].status == 1) {
                    content += '<td style="text-align:center;">대가중인 게시글 입니다.</td>';
                } else if (result.data[index].status == 2) {
                    content += '<td style="text-align:center;">' + result.data[index].title + '</td>';
                } else {
                    content += '<td style="text-align:center;">블라인드 된 게시글 입니다.</td>';
                }
                content += '<td style="text-align:center;">' + result.data[index].loginId + '</td>';
                content += '<td style="text-align:center;">' + result.data[index].registeredAt + '</td>';
                content += '</tr>';
            });
            content += '</tbody>';

            $('#divBoardListArea').html(content);
            fnSetPagingNavy(parseInt($('#hdPageNo').val()), ((result.totalCount / parseInt($('#hdPageSize').val())) + 1), 10);
        } else {
            alert(result.resultMsg);
            content += '<tr>';
            content += '<td colspan="4" style="text-align:center;">No Data</td>';
            content += '</tr>';
            content += '</tbody>';
            $('#divBoardListArea').html(content);
            $('#divBoardPageNavy').html('');
        }
    });
}

function fnBoardWriteView() {
    if (fnAuthCheck() == false) {
        alert('로그인 후 사용하실 수 있습니다.');
        fnLoginPopUp();
        return false;
    }

    $('#txt_board_write_title').val('');
    $('#txt_board_write_content').val('');
    fnCloseAllModal();
    $('#divWriteBoardModal').modal();
}

function fnBoardWrite() {
    if (fnAuthCheck() == false) {
        alert('로그인 후 사용하실 수 있습니다.');
        fnLoginPopUp();
        return false;
    }

    if ($.trim($('#txt_board_write_title').val()) == '') {
        alert('게시글 제목은 필수값 입니다.');
        return false;
    }

    if ($.trim($('#txt_board_write_content').val()) == '') {
        alert('게시글 내용은 필수값 입니다.');
        return false;
    }

    $.ajax({
        url: "/board/write",
        contentType: 'application/json; charset=utf-8;',
        data: '{"authToken": "' + fnGetAuthToken() + '", "title": "' + $('#txt_board_write_title').val() + '", "contents": "' + $('#txt_board_write_content').val().replace(/\n/g,'\\n') + '"}',
        dataType: 'json',
        type: "post",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $('#txt_board_write_title').val('');
            $('#txt_board_write_content').val('');
            alert('게시글 작성에 성공하였습니다.');
            fnCloseAllModal();
            $('#hdPageNo').val('1');
            fnGetBoardList();
        } else {
            alert(result.resultMsg);
        }
    });
}

function fnBoardDetail(contentNo) {
    if (contentNo == '' || parseInt(contentNo) <= 0) {
        alert('비정상 접속입니다.');
        return false;
    }

    $.ajax({
        url: "/board/detail",
        contentType: 'application/json',
        data: '{"authToken": "' + fnGetAuthToken() + '", "contentId": ' + contentNo + '}',
        dataType: 'json',
        type: "post",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $('#lb_board_author').text(result.data.loginId);
            $('#lb_board_reg_date').text(result.data.registeredAt);
            $('#txt_board_detail_title').val(result.data.title);
            $('#txt_board_detail_content').val(result.data.contents);
            $('#divDetailBoardModal').modal();
        } else {
            alert(result.resultMsg);
        }
    });
}

function fnSetPageNo(pageNo) {
    $('#hdPageNo').val(pageNo);
    fnGetBoardList();
}

function fnSetPagingNavy(currPage, totalPage, pageNavySize) {
    var    maxPageNavy = pageNavySize;
    var   pageTerm    = 1;
    var   pageNavyStr;
    var   pageNavyEnd;
    var   htmlTag;

    htmlTag = '<ul class="pagination">';

    if (totalPage <= maxPageNavy) {
        for (var pageNo = 1; pageNo <= totalPage; pageNo++) {
            if (pageNo == currPage) {
                htmlTag += '<li class="active" onclick="fnSetPageNo(' + pageNo + ')">' + pageNo + '</li>';
            } else {
                htmlTag += '<li class="disabled" onclick="fnSetPageNo(' + pageNo + ')">' + pageNo + '</li>';
            }
        }
    } else {
        if (currPage <= maxPageNavy) {
            pageNavyStr = 1;
        } else {
            do {
                if (maxPageNavy * pageTerm < currPage && currPage <= maxPageNavy * (pageTerm + 1)) {
                    pageNavyStr = (maxPageNavy * pageTerm) + 1;
                    break;
                }

                pageTerm = pageTerm + 1;
            } while (true);
        }

        if ((pageNavyStr + maxPageNavy - 1) < totalPage) {
            pageNavyEnd = (pageNavyStr + maxPageNavy - 1);
        } else {
            pageNavyEnd = totalPage;
        }

        if (1 < pageNavyStr) {
            htmlTag += '<li class="disabled" onclick="fnSetPageNo(1)"><<</li>';
            htmlTag += '<li class="disabled" onclick="fnSetPageNo(' + (pageNavyStr - 1) + ')"><</li>';
        }

        for (var pageNo = pageNavyStr; pageNo <= pageNavyEnd; pageNo++) {
            if (pageNo == currPage) {
                htmlTag += '<li class="active" onclick="fnSetPageNo(' + pageNo + ')">' + pageNo + '</li>';
            } else {
                htmlTag += '<li class="disabled" onclick="fnSetPageNo(' + pageNo + ')">' + pageNo + '</li>';
            }
        }

        if (pageNavyEnd < totalPage) {
            htmlTag += '<li class="disabled" onclick="fnSetPageNo(' + (pageNavyEnd + 1) + ')">></li>';
            htmlTag += '<li class="disabled" onclick="fnSetPageNo(' + totalPage + ')">>></li>';
        }
    }

    htmlTag += '</ul>';
    $('#divBoardPageNavy').html(htmlTag);
}