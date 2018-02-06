(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medic_InformationDetailController', Medic_InformationDetailController);

    Medic_InformationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medic_Information', 'Medic'];

    function Medic_InformationDetailController($scope, $rootScope, $stateParams, previousState, entity, Medic_Information, Medic) {
        var vm = this;

        vm.medic_Information = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medic_InformationUpdate', function(event, result) {
            vm.medic_Information = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
