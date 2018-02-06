(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('Medic_InformationSearch', Medic_InformationSearch);

    Medic_InformationSearch.$inject = ['$resource'];

    function Medic_InformationSearch($resource) {
        var resourceUrl =  'api/_search/medic-informations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
