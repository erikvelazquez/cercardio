(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientAppSearch', PacientAppSearch);

    PacientAppSearch.$inject = ['$resource'];

    function PacientAppSearch($resource) {
        var resourceUrl =  'api/_search/pacient-apps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
