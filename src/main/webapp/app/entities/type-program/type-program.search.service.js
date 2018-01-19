(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('Type_ProgramSearch', Type_ProgramSearch);

    Type_ProgramSearch.$inject = ['$resource'];

    function Type_ProgramSearch($resource) {
        var resourceUrl =  'api/_search/type-programs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
