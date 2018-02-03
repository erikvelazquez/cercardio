(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ReservationDialogController', ReservationDialogController);

    ReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reservation', 'Pacient', 'Medic', 'Status', 'Payment', 'Timers', 'Appreciation', 'PacientMedicalAnalysis'];

    function ReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reservation, Pacient, Medic, Status, Payment, Timers, Appreciation, PacientMedicalAnalysis) {
        var vm = this;

        vm.reservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pacients = Pacient.query();
        vm.medics = Medic.query();
        vm.statuses = Status.query();
        vm.payments = Payment.query();
        vm.timers = Timers.query();
        vm.appreciations = Appreciation.query();
        vm.pacientmedicalanalyses = PacientMedicalAnalysis.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reservation.id !== null) {
                Reservation.update(vm.reservation, onSaveSuccess, onSaveError);
            } else {
                Reservation.save(vm.reservation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:reservationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateat = false;
        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
