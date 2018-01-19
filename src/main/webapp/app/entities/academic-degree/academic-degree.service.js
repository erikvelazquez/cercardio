(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('AcademicDegree', AcademicDegree);

    AcademicDegree.$inject = ['$resource', 'DateUtils'];

    function AcademicDegree ($resource, DateUtils) {
        var resourceUrl =  'api/academic-degrees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdat = DateUtils.convertDateTimeFromServer(data.createdat);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
