(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('DomicileSearch', DomicileSearch);

    DomicileSearch.$inject = ['$resource'];

    function DomicileSearch($resource) {
        var resourceUrl =  'api/_search/domiciles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
