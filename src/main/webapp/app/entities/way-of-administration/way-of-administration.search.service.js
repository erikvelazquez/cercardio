(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('Way_of_AdministrationSearch', Way_of_AdministrationSearch);

    Way_of_AdministrationSearch.$inject = ['$resource'];

    function Way_of_AdministrationSearch($resource) {
        var resourceUrl =  'api/_search/way-of-administrations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
