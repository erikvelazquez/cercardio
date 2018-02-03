(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('TimesSearch', TimesSearch);

    TimesSearch.$inject = ['$resource'];

    function TimesSearch($resource) {
        var resourceUrl =  'api/_search/times/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
