(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('NurseSearch', NurseSearch);

    NurseSearch.$inject = ['$resource'];

    function NurseSearch($resource) {
        var resourceUrl =  'api/_search/nurses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
