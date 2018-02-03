(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('ProgramsSearch', ProgramsSearch);

    ProgramsSearch.$inject = ['$resource'];

    function ProgramsSearch($resource) {
        var resourceUrl =  'api/_search/programs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
