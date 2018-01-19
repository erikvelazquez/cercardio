(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ReservationDetailController', ReservationDetailController);

    ReservationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reservation', 'Pacient', 'Medic', 'Status', 'Payment', 'Timers', 'Appreciation', 'PacientMedicalAnalysis'];

    function ReservationDetailController($scope, $rootScope, $stateParams, previousState, entity, Reservation, Pacient, Medic, Status, Payment, Timers, Appreciation, PacientMedicalAnalysis) {
        var vm = this;

        vm.reservation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:reservationUpdate', function(event, result) {
            vm.reservation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
