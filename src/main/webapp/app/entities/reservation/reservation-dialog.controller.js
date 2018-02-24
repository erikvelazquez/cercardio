(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ReservationDialogController', ReservationDialogController);

    ReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Reservation', 'Appreciation', 'PacientMedicalAnalysis', 'Pacient', 'Medic', 'Status', 'Payment', 'Timers'];

    function ReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Reservation, Appreciation, PacientMedicalAnalysis, Pacient, Medic, Status, Payment, Timers) {
        var vm = this;

        vm.reservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.appreciations = Appreciation.query({filter: 'reservation-is-null'});
        $q.all([vm.reservation.$promise, vm.appreciations.$promise]).then(function() {
            if (!vm.reservation.appreciation || !vm.reservation.appreciation.id) {
                return $q.reject();
            }
            return Appreciation.get({id : vm.reservation.appreciation.id}).$promise;
        }).then(function(appreciation) {
            vm.appreciations.push(appreciation);
        });
        vm.pacientmedicalanalyses = PacientMedicalAnalysis.query({filter: 'reservation-is-null'});
        $q.all([vm.reservation.$promise, vm.pacientmedicalanalyses.$promise]).then(function() {
            if (!vm.reservation.pacientMedicalAnalysis || !vm.reservation.pacientMedicalAnalysis.id) {
                return $q.reject();
            }
            return PacientMedicalAnalysis.get({id : vm.reservation.pacientMedicalAnalysis.id}).$promise;
        }).then(function(pacientMedicalAnalysis) {
            vm.pacientmedicalanalyses.push(pacientMedicalAnalysis);
        });
        vm.pacients = Pacient.query();
        vm.medics = Medic.query();
        vm.statuses = Status.query();
        vm.payments = Payment.query();
        vm.timers = Timers.query();

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
