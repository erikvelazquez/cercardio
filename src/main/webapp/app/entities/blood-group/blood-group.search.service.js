(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('BloodGroupSearch', BloodGroupSearch);

    BloodGroupSearch.$inject = ['$resource'];

    function BloodGroupSearch($resource) {
        var resourceUrl =  'api/_search/blood-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
