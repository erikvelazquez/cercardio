(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('DisabilitiesSearch', DisabilitiesSearch);

    DisabilitiesSearch.$inject = ['$resource'];

    function DisabilitiesSearch($resource) {
        var resourceUrl =  'api/_search/disabilities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
