(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medical_ProceduresDialogController', Medical_ProceduresDialogController);

    Medical_ProceduresDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medical_Procedures'];

    function Medical_ProceduresDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medical_Procedures) {
        var vm = this;

        vm.medical_Procedures = entity;
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
            if (vm.medical_Procedures.id !== null) {
                Medical_Procedures.update(vm.medical_Procedures, onSaveSuccess, onSaveError);
            } else {
                Medical_Procedures.save(vm.medical_Procedures, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medical_ProceduresUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
