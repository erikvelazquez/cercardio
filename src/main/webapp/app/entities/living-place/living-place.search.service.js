(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('LivingPlaceSearch', LivingPlaceSearch);

    LivingPlaceSearch.$inject = ['$resource'];

    function LivingPlaceSearch($resource) {
        var resourceUrl =  'api/_search/living-places/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
