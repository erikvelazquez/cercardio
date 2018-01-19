(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PrivateHealthInsuranceDialogController', PrivateHealthInsuranceDialogController);

    PrivateHealthInsuranceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrivateHealthInsurance'];

    function PrivateHealthInsuranceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrivateHealthInsurance) {
        var vm = this;

        vm.privateHealthInsurance = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.privateHealthInsurance.id !== null) {
                PrivateHealthInsurance.update(vm.privateHealthInsurance, onSaveSuccess, onSaveError);
            } else {
                PrivateHealthInsurance.save(vm.privateHealthInsurance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:privateHealthInsuranceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
