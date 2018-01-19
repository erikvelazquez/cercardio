(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('Indigenous_LanguagesSearch', Indigenous_LanguagesSearch);

    Indigenous_LanguagesSearch.$inject = ['$resource'];

    function Indigenous_LanguagesSearch($resource) {
        var resourceUrl =  'api/_search/indigenous-languages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
