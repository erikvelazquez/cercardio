(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('MedicineSearch', MedicineSearch);

    MedicineSearch.$inject = ['$resource'];

    function MedicineSearch($resource) {
        var resourceUrl =  'api/_search/medicines/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
