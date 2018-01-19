(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DiagnosisDialogController', DiagnosisDialogController);

    DiagnosisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Diagnosis'];

    function DiagnosisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Diagnosis) {
        var vm = this;

        vm.diagnosis = entity;
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
            if (vm.diagnosis.id !== null) {
                Diagnosis.update(vm.diagnosis, onSaveSuccess, onSaveError);
            } else {
                Diagnosis.save(vm.diagnosis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:diagnosisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
