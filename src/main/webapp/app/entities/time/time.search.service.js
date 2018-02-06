(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('TimeSearch', TimeSearch);

    TimeSearch.$inject = ['$resource'];

    function TimeSearch($resource) {
        var resourceUrl =  'api/_search/times/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
