(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DomicileDetailController', DomicileDetailController);

    DomicileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Domicile'];

    function DomicileDetailController($scope, $rootScope, $stateParams, previousState, entity, Domicile) {
        var vm = this;

        vm.domicile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:domicileUpdate', function(event, result) {
            vm.domicile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
