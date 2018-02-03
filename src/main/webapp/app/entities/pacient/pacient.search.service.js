(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientSearch', PacientSearch);

    PacientSearch.$inject = ['$resource'];

    function PacientSearch($resource) {
        var resourceUrl =  'api/_search/pacients/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
