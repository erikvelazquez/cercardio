(function() {
    'use strict';
    angular
        .module('cercardiobitiApp')
        .factory('SocialHealthInsurance', SocialHealthInsurance);

    SocialHealthInsurance.$inject = ['$resource'];

    function SocialHealthInsurance ($resource) {
        var resourceUrl =  'api/social-health-insurances/:id';

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
