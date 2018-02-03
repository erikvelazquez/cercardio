(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('OccupationSearch', OccupationSearch);

    OccupationSearch.$inject = ['$resource'];

    function OccupationSearch($resource) {
        var resourceUrl =  'api/_search/occupations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
