(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('ReservationCalendar', ReservationCalendar);

        ReservationCalendar.$inject = ['$resource', 'DateUtils'];

    function ReservationCalendar ($resource, DateUtils) {
        var resourceUrl =  'api/reservations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateat = DateUtils.convertLocalDateFromServer(data.dateat);
                        data.timeat = DateUtils.convertDateTimeFromServer(data.timeat);
                        data.createdat = DateUtils.convertDateTimeFromServer(data.createdat);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateat = DateUtils.convertLocalDateToServer(copy.dateat);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateat = DateUtils.convertLocalDateToServer(copy.dateat);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
