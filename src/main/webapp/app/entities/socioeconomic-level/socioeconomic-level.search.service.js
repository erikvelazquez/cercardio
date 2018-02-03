(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('SocioeconomicLevelSearch', SocioeconomicLevelSearch);

    SocioeconomicLevelSearch.$inject = ['$resource'];

    function SocioeconomicLevelSearch($resource) {
        var resourceUrl =  'api/_search/socioeconomic-levels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
