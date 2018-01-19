(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Medic_InformationDialogController', Medic_InformationDialogController);

    Medic_InformationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Medic_Information', 'Medic'];

    function Medic_InformationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Medic_Information, Medic) {
        var vm = this;

        vm.medic_Information = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.medics = Medic.query({filter: 'medic_information-is-null'});
        $q.all([vm.medic_Information.$promise, vm.medics.$promise]).then(function() {
            if (!vm.medic_Information.medic || !vm.medic_Information.medic.id) {
                return $q.reject();
            }
            return Medic.get({id : vm.medic_Information.medic.id}).$promise;
        }).then(function(medic) {
            vm.medics.push(medic);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medic_Information.id !== null) {
                Medic_Information.update(vm.medic_Information, onSaveSuccess, onSaveError);
            } else {
                Medic_Information.save(vm.medic_Information, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:medic_InformationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
