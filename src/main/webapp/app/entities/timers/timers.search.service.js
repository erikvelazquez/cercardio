(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('TimersSearch', TimersSearch);

    TimersSearch.$inject = ['$resource'];

    function TimersSearch($resource) {
        var resourceUrl =  'api/_search/timers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
