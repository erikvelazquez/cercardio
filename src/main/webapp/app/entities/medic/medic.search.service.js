(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('MedicSearch', MedicSearch);

    MedicSearch.$inject = ['$resource'];

    function MedicSearch($resource) {
        var resourceUrl =  'api/_search/medics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
