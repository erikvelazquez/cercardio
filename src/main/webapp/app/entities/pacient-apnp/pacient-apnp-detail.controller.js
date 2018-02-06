(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientApnpDetailController', PacientApnpDetailController);

    PacientApnpDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientApnp', 'Pacient', 'DrugAddiction', 'Background', 'Time'];

    function PacientApnpDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientApnp, Pacient, DrugAddiction, Background, Time) {
        var vm = this;

        vm.pacientApnp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientApnpUpdate', function(event, result) {
            vm.pacientApnp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
