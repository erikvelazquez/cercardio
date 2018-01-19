(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('PacientApnp', PacientApnp);

    PacientApnp.$inject = ['$resource', 'DateUtils'];

    function PacientApnp ($resource, DateUtils) {
        var resourceUrl =  'api/pacient-apnps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datestarts = DateUtils.convertLocalDateFromServer(data.datestarts);
                        data.dateend = DateUtils.convertLocalDateFromServer(data.dateend);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datestarts = DateUtils.convertLocalDateToServer(copy.datestarts);
                    copy.dateend = DateUtils.convertLocalDateToServer(copy.dateend);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datestarts = DateUtils.convertLocalDateToServer(copy.datestarts);
                    copy.dateend = DateUtils.convertLocalDateToServer(copy.dateend);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
