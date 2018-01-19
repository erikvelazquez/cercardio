(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('SocialHealthInsuranceSearch', SocialHealthInsuranceSearch);

    SocialHealthInsuranceSearch.$inject = ['$resource'];

    function SocialHealthInsuranceSearch($resource) {
        var resourceUrl =  'api/_search/social-health-insurances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
