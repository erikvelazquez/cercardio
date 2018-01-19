(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientLaboratoyDialogController', PacientLaboratoyDialogController);

    PacientLaboratoyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PacientLaboratoy', 'Pacient'];

    function PacientLaboratoyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PacientLaboratoy, Pacient) {
        var vm = this;

        vm.pacientLaboratoy = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pacients = Pacient.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientLaboratoy.id !== null) {
                PacientLaboratoy.update(vm.pacientLaboratoy, onSaveSuccess, onSaveError);
            } else {
                PacientLaboratoy.save(vm.pacientLaboratoy, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientLaboratoyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateofelaboration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
