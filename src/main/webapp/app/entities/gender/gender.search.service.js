(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('GenderSearch', GenderSearch);

    GenderSearch.$inject = ['$resource'];

    function GenderSearch($resource) {
        var resourceUrl =  'api/_search/genders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
