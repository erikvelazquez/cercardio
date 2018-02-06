(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('AppreciationSearch', AppreciationSearch);

    AppreciationSearch.$inject = ['$resource'];

    function AppreciationSearch($resource) {
        var resourceUrl =  'api/_search/appreciations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
