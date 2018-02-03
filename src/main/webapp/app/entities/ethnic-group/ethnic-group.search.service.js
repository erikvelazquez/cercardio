(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('EthnicGroupSearch', EthnicGroupSearch);

    EthnicGroupSearch.$inject = ['$resource'];

    function EthnicGroupSearch($resource) {
        var resourceUrl =  'api/_search/ethnic-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
