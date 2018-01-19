(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('MedicNurseSearch', MedicNurseSearch);

    MedicNurseSearch.$inject = ['$resource'];

    function MedicNurseSearch($resource) {
        var resourceUrl =  'api/_search/medic-nurses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
