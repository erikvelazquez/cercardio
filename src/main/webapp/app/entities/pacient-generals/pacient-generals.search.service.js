(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('PacientGeneralsSearch', PacientGeneralsSearch);

    PacientGeneralsSearch.$inject = ['$resource'];

    function PacientGeneralsSearch($resource) {
        var resourceUrl =  'api/_search/pacient-generals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
