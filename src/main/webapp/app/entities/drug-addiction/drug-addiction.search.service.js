(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('DrugAddictionSearch', DrugAddictionSearch);

    DrugAddictionSearch.$inject = ['$resource'];

    function DrugAddictionSearch($resource) {
        var resourceUrl =  'api/_search/drug-addictions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
