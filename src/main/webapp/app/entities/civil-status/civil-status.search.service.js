(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('CivilStatusSearch', CivilStatusSearch);

    CivilStatusSearch.$inject = ['$resource'];

    function CivilStatusSearch($resource) {
        var resourceUrl =  'api/_search/civil-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
