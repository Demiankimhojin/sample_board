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
    $('#divRegisterAccountModal').modal();
}

function fnLoginPopUp() {
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

function fnInitHeader() {
    var authToken = $.cookie("auth_token");

    if (authToken == null || authToken == "") {
        $('#divLogInHeader').show();
        $('#divLogOutHeader').hide();
    } else {
        $('#divLogOutHeader').show();
        $('#divLogInHeader').hide();
    }
}

function fnGetBoardList() {
    var content = '';

    content = '<table class="table-list">';
    content += '<thead>';
    content += '<tr class="table-thead">';
    content += '<th style="width:10%;">게시물 번호</th>';
    content += '<th style="width:80%;">게시글 제목</th>';
    content += '<th style="width:10%;">작성자 계정 번호</th>';
    content += '</tr>'
    content += '</thead>';
    content += '<tbody class="table-tbody">';

    $.ajax({
        url: "/board/list?pageSize=" + $('#hdPageSize').val() + "&pageNo=" + $('#hdPageNo').val(),
        type: "get",
        error : function(result) {
            alert('System Error : ' + result.resultMsg);
            content += '<tr>';
            content += '<td colspan="3" style="text-align:center;">No Data</td>';
            content += '</tr>';
            content += '</tbody>';

            $('#divBoardListArea').html(content);
            $('#divBoardPageNavy').html('');
        }
    }).done(function(result) {
        if (result.resultCode == 0) {
            $.each(result.data, function (index) {
                content += '<tr>';
                content += '<td style="text-align:center;">' + result.data[index].id + '</td>';
                content += '<td style="text-align:center;">' + result.data[index].title + '</td>';
                content += '<td style="text-align:center;">' + result.data[index].accountId + '</td>';
                content += '</tr>';
            });
            content += '</tbody>';

            $('#divBoardListArea').html(content);
            fnSetPagingNavy(parseInt($('#hdPageNo').val()), ((result.totalCount / parseInt($('#hdPageSize').val())) + 1), 10);
        } else {
            alert(result.resultMsg);
            content += '<tr>';
            content += '<td colspan="3" style="text-align:center;">No Data</td>';
            content += '</tr>';
            content += '</tbody>';
            $('#divBoardListArea').html(content);
            $('#divBoardPageNavy').html('');
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