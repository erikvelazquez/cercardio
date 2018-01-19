(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('BackgroundSearch', BackgroundSearch);

    BackgroundSearch.$inject = ['$resource'];

    function BackgroundSearch($resource) {
        var resourceUrl =  'api/_search/backgrounds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
