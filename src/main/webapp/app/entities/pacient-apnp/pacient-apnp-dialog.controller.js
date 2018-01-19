(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientApnpDialogController', PacientApnpDialogController);

    PacientApnpDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PacientApnp', 'Pacient', 'DrugAddiction', 'Background', 'Time'];

    function PacientApnpDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PacientApnp, Pacient, DrugAddiction, Background, Time) {
        var vm = this;

        vm.pacientApnp = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pacients = Pacient.query();
        vm.drugaddictions = DrugAddiction.query();
        vm.backgrounds = Background.query();
        vm.times = Time.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacientApnp.id !== null) {
                PacientApnp.update(vm.pacientApnp, onSaveSuccess, onSaveError);
            } else {
                PacientApnp.save(vm.pacientApnp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientApnpUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datestarts = false;
        vm.datePickerOpenStatus.dateend = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
