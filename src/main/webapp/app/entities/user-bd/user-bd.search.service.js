(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .factory('UserBDSearch', UserBDSearch);

    UserBDSearch.$inject = ['$resource'];

    function UserBDSearch($resource) {
        var resourceUrl =  'api/_search/user-bds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
