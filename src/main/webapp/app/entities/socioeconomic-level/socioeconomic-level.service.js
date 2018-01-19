(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('SocioeconomicLevel', SocioeconomicLevel);

    SocioeconomicLevel.$inject = ['$resource'];

    function SocioeconomicLevel ($resource) {
        var resourceUrl =  'api/socioeconomic-levels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
