(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('UserBDDetailController', UserBDDetailController);

    UserBDDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserBD'];

    function UserBDDetailController($scope, $rootScope, $stateParams, previousState, entity, UserBD) {
        var vm = this;

        vm.userBD = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:userBDUpdate', function(event, result) {
            vm.userBD = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
