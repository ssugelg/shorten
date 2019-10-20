'use strict';

var $inputUrl = $('#encryptUrl'),
    $converBtn = $('#shortenBtn'),
    $result = $('.alert-success'),
    $autoValid = $('#auto_valid');

var utils = {
    isValidUrl: function (isShowFlag) {
        var url = $inputUrl.val();

        if (url.trim().length == 0) {
            isShowFlag && alert("입력값이 없습니다.");
            return false;
        }

        var urlRegExp = new RegExp('^(https?:\\/\\/)?((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|((\\d{1,3}\\.){3}\\d{1,3}))(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*(\\?[;&a-z\\d%_.~+=-]*)?(\\#[-a-z\\d_]*)?$','i');

        if (!urlRegExp.test(url)) {
            isShowFlag && alert('URL 형식이 부적합 합니다.');
            return false;
        }

        return true;
    },
    supplement: function (url) {
        if (!url.startsWith('http://') && !url.startsWith('https://')) {
            url = 'http://' + url;
        }

        return url.trim();
    },
    typeOf: function (obj) {
        return Object.prototype.toString.call(obj).slice(8, -1);
    },
    toggleAutoCheck: function () {
        if ($autoValid.is(':checked')) {
            $inputUrl.addClass(this.isValidUrl() ? 'valid' : 'invalid');
        } else {
            $inputUrl.removeClass('valid', 'invalid');
        }
    }
};

var shorten = (function (transfer) {

    $(document).ready(function () {
        $inputUrl.keyup(function (e) {
            $inputUrl.removeClass('valid invalid');

            if ($inputUrl.val().length != 0) {
                utils.toggleAutoCheck();
            }

            // Enter 입력
            if (e.keyCode === 13 && utils.typeOf(shorten) === 'Function') {
                shorten();
            }
        });

        utils.toggleAutoCheck();
    });

    return function () {
        var url = $inputUrl.val();

        if (utils.isValidUrl(true)) {

            $result.hide();
            $converBtn.children('span').show();

            transfer({
               url: utils.supplement(url)
            }, function (result) {
                if (!result) {
                    return false;
                }

                var returnUrl = (location.protocol + '//' + location.host + '/' + result);

                $result.find('p > span').html($('#encryptUrl').val());

                $result.find('p > a').html(returnUrl);
                $result.find('p > a').attr('href', returnUrl);

                $result.show();
                $converBtn.children('span').hide();

            }, function (e) {
                console.error(e);

                alert((e && e.responseJSON && e.responseJSON.message) || '알 수 없는 에러가 발생하였습니다. 관리자에게 문의해주세요');

                $converBtn.children('span').hide();
            });
        }
    };
}(function (data, successCallback, errorCallback) {
    $.ajax({
        method: 'post',
        url: '/api/encrypt',
        data: data,
        success: function (result) {
            successCallback(result);
        },
        error: function (e) {
            errorCallback(e);
        }
    });
}));