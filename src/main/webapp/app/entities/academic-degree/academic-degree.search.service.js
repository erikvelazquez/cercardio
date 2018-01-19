(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('AcademicDegreeSearch', AcademicDegreeSearch);

    AcademicDegreeSearch.$inject = ['$resource'];

    function AcademicDegreeSearch($resource) {
        var resourceUrl =  'api/_search/academic-degrees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
