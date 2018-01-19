(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medical_ProceduresDetailController', Medical_ProceduresDetailController);

    Medical_ProceduresDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medical_Procedures'];

    function Medical_ProceduresDetailController($scope, $rootScope, $stateParams, previousState, entity, Medical_Procedures) {
        var vm = this;

        vm.medical_Procedures = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medical_ProceduresUpdate', function(event, result) {
            vm.medical_Procedures = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
