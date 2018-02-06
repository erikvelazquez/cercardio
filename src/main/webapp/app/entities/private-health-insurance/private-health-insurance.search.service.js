(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PrivateHealthInsuranceSearch', PrivateHealthInsuranceSearch);

    PrivateHealthInsuranceSearch.$inject = ['$resource'];

    function PrivateHealthInsuranceSearch($resource) {
        var resourceUrl =  'api/_search/private-health-insurances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
