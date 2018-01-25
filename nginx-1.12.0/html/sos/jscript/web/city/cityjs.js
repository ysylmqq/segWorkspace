/*
description: 省市区三级(二级)联动
*/
var citySelector = function () {
    var jsonProvince = "../../../jscript/web/city/json-array-of-province.js";
    var jsonCity = "../../../jscript/web/city/json-array-of-city.js";
    var jsonDistrict = "../../../jscript/web/city/json-array-of-district.js";
    var initProvince = "<option value='0'>请选择省份</option>";
    var initCity = "<option value='0'>请选择城市</option>";
    var initDistrict = "<option value='0'>请选择区县</option>";
    return {
        Init: function (province, city, district, preProvince, preCity, preDistrict, hasDistrict) {
            var that = this;
            that._LoadOptions(jsonProvince, preProvince, province, null, 0, initProvince);
            province.change(function () {
                that._LoadOptions(jsonCity, preCity, city, province, 2, initCity);
            });
            if (hasDistrict) {
                city.change(function () {
                    that._LoadOptions(jsonDistrict, preDistrict, district, city, 4, initDistrict);
                });
                province.change(function () {
                    city.change();
                });
            }
            setTimeout(function () {
                province.change();
            }, 100);
        },
        _LoadOptions: function (datapath, preobj, targetobj, parentobj, comparelen, initoption) {
            $.get(
                datapath,
                function (r) {
                    var t = ''; // t:    html容器 
                    var s;      // s:    选中标识 
                    var pre;    // pre:  初始值
                    if (preobj === undefined || preobj === null) {
                        pre = 0;
                    } else {
                        pre = preobj.val();
                    }
                    for (var i = 0; i < r.length; i++) {
                        s = '';
                        if (comparelen === 0) {
                            if (pre !== "" && pre !== 0 && r[i].code === pre) {
                                s = ' selected=\"selected\" ';
                                pre = '';
                            }
                            t += '<option value=' + r[i].code + s + '>' + r[i].name + '</option>';
                        }
                        else {
                            var p = parentobj.val();
                            if (p.substring(0, comparelen) === r[i].code.substring(0, comparelen)) {
                                if (pre !== "" && pre !== 0 && r[i].code === pre) {
                                    s = ' selected=\"selected\" ';
                                    pre = '';
                                }
                                t += '<option value=' + r[i].code + s + '>' + r[i].name + '</option>';
                            }
                        }

                    }
                    if (initoption !== '') {
                        targetobj.html(initoption + t);
                    } else {
                        targetobj.html(t);
                    }
                },
                "json"
                );
        }
    };
} ();