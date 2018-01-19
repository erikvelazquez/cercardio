(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BackgroundDetailController', BackgroundDetailController);

    BackgroundDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Background'];

    function BackgroundDetailController($scope, $rootScope, $stateParams, previousState, entity, Background) {
        var vm = this;

        vm.background = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:backgroundUpdate', function(event, result) {
            vm.background = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
