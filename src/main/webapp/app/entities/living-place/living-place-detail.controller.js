(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('LivingPlaceDetailController', LivingPlaceDetailController);

    LivingPlaceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LivingPlace'];

    function LivingPlaceDetailController($scope, $rootScope, $stateParams, previousState, entity, LivingPlace) {
        var vm = this;

        vm.livingPlace = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:livingPlaceUpdate', function(event, result) {
            vm.livingPlace = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
